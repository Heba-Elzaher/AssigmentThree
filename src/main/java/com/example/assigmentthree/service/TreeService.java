package com.example.assigmentthree.service;

import com.example.assigmentthree.model.TreeData;

import java.util.List;

public interface TreeService {
    TreeData uploadTree(TreeData tree);

    TreeData getTreeById(long id);

    List<TreeData> getAllTrees();

    void deleteTree(long id);
}
