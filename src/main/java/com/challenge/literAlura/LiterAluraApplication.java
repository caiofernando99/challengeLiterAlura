package com.challenge.literAlura;

import com.challenge.literAlura.model.DadosLivros;
import com.challenge.literAlura.service.ConsumoApi;
import com.challenge.literAlura.service.ConverteDados;
import com.challenge.literAlura.service.DadosLivrosWrapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);

	}
	@Override
	public void run(String... args) throws Exception {
		// Instanciar o scanner apenas uma vez para evitar problemas
		Scanner scanner = new Scanner(System.in);
		int readOption;

		// Loop do menu
		do {
			// Menu
			System.out.println(
					"=========================================\n" +
							"1- Buscar livro\n" +
							"2- Lista de livros registrados(no banco de dados)\n" +
							"3- Lista de autores registrados(no banco de dados)\n" +
							"4- Lista de autores vivos em determinado ano(no banco de dados)\n" +
							"5- Lista de livros registrados por idioma(no banco de dados)\n" +
							"0- SAIR\n" +
							"=========================================\n");

			// Ler opção do usuário
			System.out.print("Digite a opção desejada: ");
			readOption = Integer.parseInt(scanner.nextLine());

			ConsumoApi consumoApi = new ConsumoApi();

			switch (readOption) {
				case 1:
					System.out.println("Opção 1 selecionada: Buscar livro");
					System.out.print("Digite o nome do livro: ");

					// Pegar dado do título
					String title = scanner.nextLine();

					String encodedTitle = java.net.URLEncoder.encode(title, "UTF-8");

					String json = consumoApi.obterDados("https://gutendex.com/books/?search=" + encodedTitle);

					ConverteDados converteDados = new ConverteDados();
					DadosLivrosWrapper dadosWrapper = converteDados.obterDados(json, DadosLivrosWrapper.class);

					if (dadosWrapper != null && dadosWrapper.results() != null) {
						for (DadosLivros livro : dadosWrapper.results()) {
							System.out.println("Título: " + livro.title());
							System.out.println("Autores: " + livro.authors().stream().map(DadosLivros.Autor::name).toList());
							System.out.println("Idiomas: " + livro.languages());
							System.out.println("Downloads: " + livro.downloads());
							System.out.println("---------");
						}
					} else {
						System.out.println("Nenhum resultado encontrado.");
					}
					break;

				case 2:
					System.out.println("Opção 2 selecionada: Lista de livros registrados");
					break;

				case 3:
					System.out.println("Opção 3 selecionada: Lista de autores registrados");
					break;

				case 4:
					System.out.println("Opção 4 selecionada: Lista de autores vivos em determinado ano");
					break;

				case 5:
					System.out.println("Opção 5 selecionada: Lista de livros registrados por idioma");
					break;

				case 0:
					System.out.println("Saindo...");
					break;

				default:
					System.out.println("Opção inválida. Tente novamente.");
			}
		} while (readOption != 0);

		// Fechar o scanner ao sair do loop
		scanner.close();
	}}

