package com.example.springboot.controller;

import com.example.springboot.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.springboot.repo.PostRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@RequestParam String title, @RequestParam String anons, @RequestParam String text, Model model) {
        Post post = new Post(title, anons, text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty())
            return "redirect:/blog";
        List<Post> postList = new ArrayList<>();
        post.ifPresent(postList::add);
        model.addAttribute("post", postList);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String edit(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty())
            return "redirect:/blog";
        List<Post> postList = new ArrayList<>();
        post.ifPresent(postList::add);
        model.addAttribute("post", postList);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogUpdatePost(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setText(text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogRemovePost(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }

}
















