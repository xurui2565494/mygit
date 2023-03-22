package com.ruoyi.web.controller.system;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.utils.GsonUtils;
import com.ruoyi.web.utils.HttpUtil;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@Controller
public class SysLoginController extends BaseController
{
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe)
    {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }
    
    @PostMapping("/faceLogin")
    @ResponseBody
    public AjaxResult faceLogin(String image)
    {
    	
    	// 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", image);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "group1");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");
            map.put("user_id", "user1");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.2062d592e431ea37d8030782aeda07b4.2592000.1576427644.282335-17749198";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            
            Map<String, Object> rstMap = GsonUtils.fromJson(result, Map.class);
            
            Map<String, Object> resultMap = (Map<String, Object>)rstMap.get("result");
            
            if(resultMap != null && !resultMap.isEmpty()) {
            
	            List<Map<String, Object>> userList  = (List<Map<String, Object>>) resultMap.get("user_list");
	            
	            if(userList != null && userList.size() > 0) {
	            	
	            	Map<String, Object> uMap = userList.get(0);
	            	
	            	Double score = Double.valueOf(String.valueOf(uMap.get("score")));
	            	
	            	System.out.println(score);
	            	
	            	if(score > 90) {
	            		
	            		return success();
	            		
	            	}else {
	            		
	            		return error("登录失败！");
	            		
	            	}
	            	
	            }else {
	            	
	            	return error("登录失败！");
	            }
            
            }else {
            	return error((String)rstMap.get("error_msg"));
            }
        }catch(Exception e) {
        	
        	return error(e.getMessage());
        	
        }  
    	
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }
}
