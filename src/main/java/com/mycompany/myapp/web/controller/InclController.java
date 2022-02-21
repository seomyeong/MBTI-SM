package com.mycompany.myapp.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.myapp.community.service.CommunityService;
import com.mycompany.myapp.domain.Member;

@Controller
public class InclController {
	@Autowired
	CommunityService communityService;
	
	@ResponseBody
	@PostMapping("/liveProfile")
	public Map<String, Object> liveProfile(@RequestBody Map<String, String> param) {
		
		long loginId = Long.parseLong(param.get("loginId"));
		
		Member m = communityService.findMemberByMemberId(loginId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("m",m);
		
		return map;
	}
}
