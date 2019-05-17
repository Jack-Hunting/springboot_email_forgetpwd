package com.qf.controller;

import com.google.gson.Gson;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.qf.utils.MailUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @vervion 1.0
 * @date 2019/05/16 20:39
 * @user Jack-Hunting
 */
@RequestMapping("emailController")
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 给用户注册时的邮箱发送验证码
     */
    @RequestMapping(value="/sendMess",produces="text/html;charset=utf-8")
    @ResponseBody
    public String sendMessage(String email, HttpServletRequest req){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //生成随机数验证码
            int code = (int)(Math.random()*(9999+1000));

            //将生成的验证码放到session中
            req.getSession().setAttribute("code", code+"");

            //发送邮件
            MailUtil.send_mail(email, "验证码", "您在注册学生管理系统时的验证码是："+code, "");

            map.put("message", "验证码已发送");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(map);
    }

    /**
     * 注册验证
     */
    @RequestMapping(value="/toRegister",produces="text/html;charset=utf-8")
    public String toRegister(User user, String checkcode, HttpServletRequest req, HttpServletResponse resp){
        String code = (String)req.getSession().getAttribute("code");
        if(!userService.isExist(user)&&checkcode.equals(code)){
            userService.addUser(user);
        }else if(!checkcode.equals(code)){
            return "register";
        }

        return "login";

    }

    /**
     * 登陆验证
     */
    @RequestMapping("/login")
    public String login(User user){
        boolean exist = userService.isExist(user);

        if (exist) {
            return "ok";
        }

        return "login";
    }

    /**
     * 通过用户输入的用户名给其绑定的邮箱发送找回密码的邮件(包含链接)
     */
    @RequestMapping(value="/sendMessToModify")
    public String sendMessToModify(String username){
        User user = userService.getUserByUsername(username);

        String mess = "尊敬的"+user.getUsername()+
                "先生,您在学生管理系统中选择了找回密码,为了您的正常使用,请点击链接:" +
                "http://10.36.144.28:8080/emailController/jsp_modify/"+username;
        try {
            MailUtil.send_mail(user.getEmail(), "找回密码校验", mess, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "inputing";

    }

    /**
     * 修改密码
     */
    @RequestMapping(value="/toModifyPwd")
    public String modifyPwd(String username,String password){
        boolean ind = userService.updatePwd(username,password);

        return "login";

    }
    /**
     * 进入注册页面
     */
    @RequestMapping(value="/jsp_register")
    public String jsp_register(){
        return "register";

    }
    /**
     * 进入忘记密码页面
     */
    @RequestMapping(value="/jsp_forgetPwd")
    public String jsp_forgetPwd(){
        return "forgetPwd";

    }
    /**
     * 进入忘记密码页面
     */
    @RequestMapping(value="/jsp_modify/{username}")
    public String jsp_modifyPwd(@PathVariable String username, Model model){
        model.addAttribute("username",username);

        return "modifyPwd";

    }
    /**
     * 通过首页进入登陆页面
     */
    @RequestMapping(value="/toLogin")
    public String toLogin(){
        return "login";

    }
}
