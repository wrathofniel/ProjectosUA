package pt.ua.estga;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String [] args) {
        if (args.length < 2 || args[0].equals("--help")) {
            System.out.println("criarRespostas [INPUT_FILE] [OUTPUT_FILE] [--help]");
            System.out.println("Lê o ficheiro de texto com as respostas de cada aluno e " +
                    "cria um ficheiro binário com as respostas");
            System.out.println("");
            System.out.println("[input_file]    Nome do ficheiro de texto com as respostas de cada aluno.");
            System.out.println("[output_file]   Nome do ficheiro binário com as respostas.");
            System.out.println("--help          Mostra esta ajuda.");
            System.exit(0);
        }
        String inputFile = args[0];
        String outputFile = args[1];
        File input = new File(inputFile);
        if (!input.exists()) {
            System.err.println("Ficheiro de input não existe: " + inputFile);
            System.exit(1);
        }
        if (!input.isFile()) {
            System.err.println("Ficheiro de input não é válido: " + inputFile);
            System.exit(1);
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            String line;
            ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                ArrayList<Integer> innerList = new ArrayList<Integer>();
                for (String value : values) {
                    innerList.add(Integer.parseInt(value.trim()));
                }
                list.add(innerList);
            }

            br.close();

            // Output do ficheiro lido para verificação de dados
            for (ArrayList<Integer> innerList : list) {
                for (Integer value : innerList) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }

            // Serializa o ArrayList de ArrayLists e guarda no ficheiro de output
            FileOutputStream fos = new FileOutputStream(outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
            fos.close();
            System.out.println("Dados gravados no ficheiro < " + outputFile + " > com sucesso.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
