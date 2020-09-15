package com.rudypark3091.api.billboardTop100.web;

import com.rudypark3091.api.billboardTop100.service.GetService;
import com.rudypark3091.api.billboardTop100.web.dto.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class IndexController {

    private GetService getService = new GetService();
    private ArrayList<ResponseDto> responseDtos;

    @GetMapping("/")
    String index() {
        return "go to /{rank number you want to know} and you'll see 1~n rank of billboard chart";
    }

    @GetMapping("/{rank}")
    ArrayList<ResponseDto> rank(@PathVariable int rank) throws Exception {
        this.responseDtos = getService.findAllSongs(rank);
        return this.responseDtos;
    }
}
