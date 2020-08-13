/*
---------------------------------------------------------------------------------
Trabalho Prático - Práticas de Programação Orientada a Objetos - GCC178 - 2020/01
----------------Grupo 05 - Fila de veículos em pedágio rodoviário----------------
    Integrantes:
        Caio de Oliveira (10A - 201820267),
        Ismael Martins Silva (10A - 201820281),
        Layse Cristina Silva Garcia (10A - 201811177),
        Luiz Felipe Montuani e Silva (10A - 201920253).
---------------------------------------------------------------------------------
*/

package Plotador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.jfree.chart.JFreeChart;

public class GerenciadorDeGraficos {
	private String nomeArquivo;
	private HashMap<String, Grafico> graficos;
	
	public GerenciadorDeGraficos(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
		graficos = new HashMap<String, Grafico>();
	}
	
	public ArrayList<JFreeChart> getGraficos() {
		inicializarDadosGraficos();
		ArrayList<JFreeChart> graficos = new ArrayList<JFreeChart>();
		ArrayList<String> titulos = new ArrayList<String>(this.graficos.keySet());
		Collections.sort(titulos);
		for (String titulo: titulos) {
			Grafico grafico = this.graficos.remove(titulo);
			graficos.add(grafico.creatJFreeChart(titulo));
		}
		
		return graficos;
	}
	
	private void addGrafico(String titulo, String labelX, String labelY) {
		if (!graficos.containsKey(titulo))
			graficos.put(titulo, new Grafico(labelX, labelY));
	}
	
	private void addValorGrafico(String titulo, String nome, int valor) {
		if (graficos.containsKey(titulo))
			graficos.get(titulo).addValorLinha(nome, valor);
	}
	
	private ArrayList<String[]> lerDadosDoArquivo() {
		try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
			ArrayList<String[]> dados = new ArrayList<String[]>();
			String linha = br.readLine();
			while (linha != null && !linha.equals("")) {
				dados.add(linha.split(","));

				linha = br.readLine();
			}
			
			return dados;
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new RuntimeException("Diretorio de trabalho: " + System.getProperty("user.dir"));
		}
	}
	
	private void inicializarDadosGraficos() {
		ArrayList<String[]> dados = lerDadosDoArquivo();
		String labelX = null,
			   nome = null,
		   	   labelY[] = null,
			   titulo[] = null;
		int tamanho;
		
		for (String[] campos: dados) {
			try {
				Integer.parseInt(campos[0]);
				
				tamanho = campos.length - 1;
				for (int i = 0; i < tamanho; i ++) {
					addValorGrafico(titulo[i], nome, Integer.parseInt(campos[i + 1]));
				}
			}
			catch (Exception e) {
				if (nome != null && titulo != null) {
					labelX = null;
					labelY = null;
					titulo = null;
					nome = null;
				}
				
				if (nome == null) {
					nome = campos[0];
					for (int i = 1; i < campos.length; i ++) {
						nome += " " + campos[i];
					}
				}
				else if (titulo == null) {
					tamanho = campos.length - 1;
					
					labelX = campos[0];
					labelY = new String[tamanho];
					titulo = new String[tamanho];
					for (int i = 0; i < tamanho; i ++) {
						labelY[i] = campos[i + 1];
						titulo[i] = labelY[i] + " x " + labelX;
						addGrafico(titulo[i], labelX, labelY[i]);
					}
				}
			}
		}
	}
}
