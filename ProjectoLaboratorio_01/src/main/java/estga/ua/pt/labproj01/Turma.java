package estga.ua.pt.labproj01;

/**
 * @author IVO DANIEL SOARES DA SILVA
 * @author VICENTE REIS SILVA
 */
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Classe que representa uma Turma de Alunos
 */
public class Turma {
    private final ArrayList<Aluno> alunos;
    private double somaNotas;
    private double notaMaisAlta;
    private double notaMaisBaixa;
    private double mediaNotas;
    private int numPositivas;
    private int numNegativas;
    private double pPositivas;
    private double pNegativas;

    /**
     * Construtor da classe Turma
     */
    public Turma() {
        this.alunos = new ArrayList<Aluno>();
        this.somaNotas = 0;
        this.notaMaisAlta = 0;
        this.notaMaisBaixa = 20;
        this.mediaNotas = 0;
        this.numPositivas = 0;
        this.numNegativas = 0;
        this.pPositivas = 0;
        this.pNegativas = 0;
    }

    /**
     * Adiciona um aluno à turma
     * @param aluno Aluno a adicionar
     */
    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    /**
     * Calcula as estatísticas da turma
     * @param chave Chave com as respostas certas
     */
    public void calcularEstatisticas(int[] chave) {
        double nota = 0;

        for (Aluno aluno : alunos) {
            aluno.calcularNota(chave);
            nota = aluno.nota;
            somaNotas += nota;

            if (nota > notaMaisAlta) {
                notaMaisAlta = nota;
            }

            if (nota < notaMaisBaixa) {
                notaMaisBaixa = nota;
            }

            if (nota >= 9.5) {
                numPositivas++;
            } else if (nota < 9.5) {
                numNegativas++;
            }
        }

        mediaNotas = somaNotas / alunos.size();
        pPositivas = numPositivas * 100.0 / alunos.size();
        pNegativas = numNegativas * 100.0 / alunos.size();
    }

    /**
     * Imprime as estatísticas da turma na consola
     */
    public void imprimirEstatisticas() {
        System.out.println("Classificação de cada aluno:");
        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
        System.out.println("Nota mais alta: " + notaMaisAlta);
        System.out.println("Nota mais baixa: " + notaMaisBaixa);
        System.out.println("Média das notas: " + String.format("%.1f", mediaNotas));
        System.out.println("Número de positivas: " + numPositivas + " (" + String.format("%.1f", pPositivas) + "%)");
        System.out.println("Número de negativas: " + numNegativas + " (" + String.format("%.1f", pNegativas) + "%)");
    }

    /**
     * Escreve as estatísticas da turma em um ficheiro de texto
     *
     * @param nomeArquivo Nome do ficheiro de texto
     */
    public void escreverEstatisticas(String nomeArquivo) {
        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            writer.write("Classificação de cada aluno:\n");
            for (Aluno aluno : alunos) {
                writer.write(aluno + "\n");
            }
            writer.write("Nota mais alta: " + notaMaisAlta + "\n");
            writer.write("Nota mais baixa: " + notaMaisBaixa + "\n");
            writer.write("Média das notas: " + String.format("%.1f", mediaNotas) + "\n");
            writer.write("Número de positivas: " + numPositivas + " (" + String.format("%.1f", pPositivas) + "%)\n");
            writer.write("Número de negativas: " + numNegativas + " (" + String.format("%.1f", pNegativas) + "%)\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever arquivo: " + e.getMessage());
        }
    }
}