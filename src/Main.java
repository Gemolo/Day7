import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        MyTreeNode tree = fileCommandToTree("commandLine.txt");
        System.out.println(sumOfAllWeights(tree));
        //System.out.println(sumOfAllWeightsUnderALimit(tree, 100000));
    }

    /*
    public static int sumOfAllWeightsUnderALimit(MyTreeNode treeNode, int limit){
        int somma = treeNode.getPeso();
        if(somma >= limit) somma = 0;
        List<MyTreeNode> children = treeNode.getChildren();
        for (int i = 0; i < children.toArray().length; i++) {
            int somma1 = sumOfAllWeightsUnderALimit((MyTreeNode) children.get(i), limit);
            if(somma1 + somma < limit)   somma += somma1;
        }
        return somma;

    }
*/
    public static int sumOfAllWeights(MyTreeNode treeNode){
        int somma = treeNode.getPeso();
        List children = treeNode.getChildren();
        for (int i = 0; i < children.toArray().length; i++) {
            somma += sumOfAllWeights((MyTreeNode) children.get(i));
        }
        return somma;
    }

    private static MyTreeNode fileCommandToTree(String path) {
        MyTreeNode tree;
        String commandLine = fileToString(path);
        String[] arrCommands = commandLine.split("\n");

        if(arrCommands[0].trim().equals("$ cd /")) tree = new MyTreeNode("/", 0);
        else return null;
        MyTreeNode root = tree;
        for (int i = 1; i < arrCommands.length; i++) {
            //System.out.println(arrCommands[0]);
            //System.out.println(arrCommands[i]);
            if(arrCommands[i].contains("$")){
                String[] command = arrCommands[i].split(" ");
                switch (command[1]) {
                    case "cd" -> {
                        switch (command[2]) {
                            case "..\r" -> {
                                tree = tree.getParent();
                            }
                            case "/" -> {
                                while (!tree.getData().equals("/")) tree = tree.getParent();
                            }
                            default ->
                                    tree = tree.getChild(command[2]);
                        }
                    }
                    case "ls" -> {
                        List<MyTreeNode> children = tree.getChildren();
                    }
                }
            } else {
                String[] command = arrCommands[i].split(" ");
                switch (command[0]){
                    case "dir":
                        if (!tree.hasChild(command[1])) {
                            tree.addChild(command[1], 0);
                        }
                        break;
                    default:
                        if(!tree.hasChild(command[1])){
                            tree.addChild(command[1], parseInt(command[0]));
                        }
                }
            }
        }
        return root;
    }


    public static String fileToString(String path){
        String commandLine = "";
        Path filePath = Path.of(path);
        try {
            commandLine = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commandLine;
    }
}