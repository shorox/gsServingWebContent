package hello.controller;

import hello.model.Message;
import hello.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(Model model) {

        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model){
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("/main")
    public String addNewMessage (@RequestParam String text, @RequestParam String tag, Model model) {
        Message message = new Message(text, tag);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter (@RequestParam String filter, Model model) {
        Iterable<Message> messages;

        if(filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        }else{
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);

        return "main";
    }
}