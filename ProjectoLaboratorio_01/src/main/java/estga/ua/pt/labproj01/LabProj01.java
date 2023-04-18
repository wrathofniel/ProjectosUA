package estga.ua.pt.labproj01;

/**
 *
 * @author 47285 IVO DANIEL SOARES DA SILVA
 * @author 108519 VICENTE REIS SILVA
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LabProj01 {
    public static void main(String[] args) {
        // Lê a chave de respostas corretas
        int[] chave = lerChave("respostas.txt" , 20);

        // Cria a turma
        Turma turma = new Turma();

        File arquivo = new File("respostasTurma.old");
        ArrayList<ArrayList<Integer>> respostasTurma = null;

        try {
            FileInputStream fileIn = new FileInputStream(arquivo);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            respostasTurma = (ArrayList<ArrayList<Integer>>) objectIn.readObject();

            objectIn.close();
            fileIn.close();

            System.out.println("Notas lidas com sucesso.");

        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("Classe não encontrada: " + e.getMessage());
            return;
        }

        for (int i = 0; i < respostasTurma.size(); i++) {
            //System.out.print("Index " + arrayIndex + ": ");
            ArrayList<Integer> respostas = respostasTurma.get(i);
            Aluno aluno = new Aluno(i, respostas);
            turma.adicionarAluno(aluno);
        }

        // Calcula as estatísticas da turma
        turma.calcularEstatisticas(chave);
        // Imprime as estatísticas da turma
        turma.imprimirEstatisticas();
        // Escreve as estatísticas da turma num ficheiro
        turma.escreverEstatisticas("estatisticas2.txt");
    }

    /**
     * Lê a chave de respostas corretas
     * @param nomeFicheiro Nome do ficheiro com a chave
     * @param tamanho Tamanho da chave
     * @return Array com a chave de respostas corretas
     */
    private static int[] lerChave(String nomeFicheiro, int tamanho) {
        int[] chave = new int[tamanho];
        File ficheiro = new File(nomeFicheiro);
        try {
            Scanner scanner = new Scanner(ficheiro);
            int i = 0;
            while (scanner.hasNextLine() && i < tamanho) {
                String linha = scanner.nextLine();
                int resposta = Integer.parseInt(linha);
                chave[i] = resposta;
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado: " + e.getMessage());
        }
        return chave;
    }
}