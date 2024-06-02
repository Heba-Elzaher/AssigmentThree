package com.example.assigmentthree.controller;

import com.example.assigmentthree.model.TreeData;
import com.example.assigmentthree.openAI.Analysis;
import com.example.assigmentthree.openAI.PlantID;
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
    private Analysis analysis;


    @Autowired
    public TreeController(TreeService treeService, Analysis analysis) {
        super();
        this.treeService = treeService;
        this.analysis = analysis;
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

            int points = 5;
            List<TreeData> trees = treeService.getAllTrees();
            for (int i = 0; i < trees.size(); i++) {
                if (trees.get(i).getUser_id() == user_id) {
                    points += 5;
                }
            }
            tree.setPoints(points);
            System.out.println("Tree: " + tree.getTree_id());
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

    @PutMapping(value = "/analysis")
    public ResponseEntity<?> aiAnalysis(@RequestBody long tree_id) {
        try {
            TreeData treeData = treeService.getTreeById(tree_id);
            analysis.treeSubmission(treeData, tree_id);

            return new ResponseEntity<>("Tree analysed ", HttpStatus.CREATED);
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
