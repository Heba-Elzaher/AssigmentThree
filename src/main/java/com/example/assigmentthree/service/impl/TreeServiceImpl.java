package com.example.assigmentthree.service.impl;

import com.example.assigmentthree.exception.ResourceNotFoundException;
import com.example.assigmentthree.model.TreeData;
import com.example.assigmentthree.repository.TreeRepository;
import com.example.assigmentthree.service.TreeService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TreeServiceImpl implements TreeService {

    private TreeRepository treeRepository;

    public TreeServiceImpl(TreeRepository treeRepository) {
        super();
        this.treeRepository = treeRepository;
    }

    @Override
    public TreeData uploadTree(TreeData tree) {

        return treeRepository.save(tree);
    }

    @Override
    public TreeData getTreeById(long id) {
        return treeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tree", "Id", id));
    }

    @Override
    public List<TreeData> getAllTrees() {
        return treeRepository.findAll();
    }


    @Override
    public void deleteTree(long id) {
        treeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tree", "Id", id));
        treeRepository.deleteById(id);
    }
}
