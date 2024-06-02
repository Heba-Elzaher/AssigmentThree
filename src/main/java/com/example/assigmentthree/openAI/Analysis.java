package com.example.assigmentthree.openAI;

import com.example.assigmentthree.model.TreeData;
import com.example.assigmentthree.repository.TreeRepository;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class Analysis {

    private final GPT gpt;
    private final PlantID plantID;
    private TreeRepository treeRepository;

    public Analysis(GPT gpt, PlantID plantID, TreeRepository treeRepository) {
        this.gpt = gpt;
        this.plantID = plantID;
        this.treeRepository = treeRepository;
    }

    public Mono<String> treeSubmission(TreeData tree, long treeId) {
        byte[] imageBytes = tree.getPhoto();

        String species = plantID.treeIdentifier(imageBytes);

        String treeDetails = gpt.getAiAnalysis(species);

        // Parse and save tree information
        parseAndSaveTreeInfo(tree, treeDetails, species);

        return null;
    }

    private void parseAndSaveTreeInfo(TreeData tree, String treeDetails, String species) {
        int infoStartIndex = treeDetails.indexOf("1.");
        if (infoStartIndex != -1) {
            String relevantTreeInfo = treeDetails.substring(infoStartIndex);
            String[] infoSections = relevantTreeInfo.split("\\n\\n");

            tree.setSpecies(species);
            tree.setEndangerment(infoSections[0]);
            tree.setTips(infoSections[1]);
            tree.setRarity(infoSections[2]);
            treeRepository.save(tree);
        } else {
            System.out.println("Error: Information sections not found.");
        }
    }
}
