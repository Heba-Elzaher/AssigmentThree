package com.example.assigmentthree.controller;

import com.example.assigmentthree.model.TreeData;
import com.example.assigmentthree.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class TreeController {
    private TreeService treeService;


    @Autowired
    public TreeController(TreeService treeService) {
        super();
        this.treeService = treeService;
    }

    @PostMapping(value = "/uploadTree")
    public ResponseEntity<?> addTree(
            @RequestParam("photo") MultipartFile treePhoto,
            @RequestParam("location") String location,
            @RequestParam("date") String date,
            @RequestParam("user_id") long user_id) {

        try {
            byte[] photoBytes = treePhoto.getBytes();
            TreeData tree = new TreeData();
            tree.setPhoto(photoBytes);
            tree.setLocation(location);
            tree.setDate(date);
            tree.setUser_id(user_id);

            TreeData savedTree = treeService.uploadTree(tree);

            return new ResponseEntity<>(savedTree, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing tree photo", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error adding tree: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/getTree/{id}")
    public ResponseEntity<TreeData> getTree(@PathVariable("id") long treeId) {
        return new ResponseEntity<>(treeService.getTreeById(treeId), HttpStatus.OK);
    }

    @GetMapping(value = "/allTrees")
    public List<TreeData> getTrees() {
        return treeService.getAllTrees();
    }

    @DeleteMapping(value = "/deleteTree/{id}")
    public ResponseEntity<String> deleteTree(@PathVariable("id") long treeId) {
        treeService.deleteTree(treeId);
        return new ResponseEntity<String>("Tree deleted!", HttpStatus.OK);
    }
}
