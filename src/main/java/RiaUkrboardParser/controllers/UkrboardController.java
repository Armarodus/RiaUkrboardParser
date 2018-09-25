package RiaUkrboardParser.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import RiaUkrboardParser.models.Post;
import RiaUkrboardParser.services.UkrboardService;

@RestController
public class UkrboardController {
	
	@Autowired
	private UkrboardService service;
	
	@PostMapping("/ukrboard")
	public List<Post> getPostList(@RequestParam String querry, @RequestParam Integer count) throws IOException, InterruptedException {
		return service.getPostListByQuerry(querry, count);
	}
	
}
