package RiaUkrboardParser.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import RiaUkrboardParser.models.Post;
import RiaUkrboardParser.services.RiaService;

@RestController
public class RiaController {
	
	@Autowired
	private RiaService service;
	
	@PostMapping("/ria")
	public List<Post> getPostListRia(@RequestParam String querry, @RequestParam Integer count) throws IOException, InterruptedException {
		return service.getPostListByQuerry(querry, count);
	}
}
