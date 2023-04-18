package estga.ua.pt.labproj01;

/**
 * @author IVO DANIEL SOARES DA SILVA
 * @author VICENTE REIS SILVA
 */
import java.util.ArrayList;

/**
 * Classe que representa um Aluno
 */
public class Aluno {

    private int id;
    private ArrayList<Integer> respostas;
    public double nota;

    /**
     * Construtor da classe Aluno
     * @param id Identificador do aluno
     * @param respostas Lista de respostas do aluno
     */
    public Aluno(int id, ArrayList<Integer> respostas) {
        this.id = id;
        this.respostas = respostas;
        this.nota = 0;
    }

    /**
     * Calcula a nota do aluno
     * @param chave Chave de respostas corretas
     */
    public void calcularNota(int[] chave) {
        double pontos = 0;
        for (int i = 0; i < respostas.size(); i++) {
            if (respostas.get(i) == chave[i]) {
                pontos += 1;
            } else if (respostas.get(i) != 0) {
                pontos -= 0.5;
            }
        }
        // Se a nota for negativa, atribuir 0
        nota = pontos < 0 ? 0 : pontos;
        //nota = pontos;
    }
}