package com.challenge.literAlura;

import com.challenge.literAlura.model.Autor;
import com.challenge.literAlura.model.DadosLivros;
import com.challenge.literAlura.model.Livro;
import com.challenge.literAlura.repository.AutorRepository;
import com.challenge.literAlura.repository.LivroRepository;
import com.challenge.literAlura.service.ConsumoApi;
import com.challenge.literAlura.service.ConverteDados;
import com.challenge.literAlura.model.DadosLivrosWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Scanner;

@Transactional
@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);

	}
	@Override
	public void run(String... args) throws Exception {

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
			System.out.print("Digite a opção desejada: ");
			readOption = Integer.parseInt(scanner.nextLine());

			ConsumoApi consumoApi = new ConsumoApi();
			ConverteDados converteDados = new ConverteDados();

			switch (readOption) {
				case 1:
					System.out.println("Opção 1 selecionada: Buscar livro\n");
					System.out.print("Digite o nome do livro: ");

					// Pegar dado do título
					String title = scanner.nextLine();

					String encodedTitle = java.net.URLEncoder.encode(title, "UTF-8");

					String json = consumoApi.obterDados("https://gutendex.com/books/?search=" + encodedTitle);


					DadosLivrosWrapper dadosWrapper = converteDados.obterDados(json, DadosLivrosWrapper.class);

					if (dadosWrapper != null && dadosWrapper.results() != null) {
						for (DadosLivros livroAPI : dadosWrapper.results()) {
							String authorName = livroAPI.authors().stream().findFirst().get().name();
							Autor author = autorRepository.findByNome(authorName);

							if (author == null) {
								author = new Autor();
								author.setNome(authorName);

								Integer birthYear = livroAPI.authors().stream().findFirst().map(DadosLivros.Autor::birthYear).orElse(null);
								Integer deathYear = livroAPI.authors().stream().findFirst().map(DadosLivros.Autor::deathYear).orElse(null);

								if (birthYear != null) {
									author.setDataNascimento(birthYear);
								}
								if (deathYear != null) {
									author.setDataMorte(deathYear);
								}

								autorRepository.save(author);
								System.out.println("Autor Salvo: " + author); // Log para verificar o autor salvo
							} else {
								System.out.println("Autor Encontrado: " + author); // Log para verificar o autor encontrado
							}

							Livro livroSalvar = new Livro();
							livroSalvar.setTitle(livroAPI.title());
							livroSalvar.setDownloadCount(livroAPI.downloads());
							livroSalvar.setLanguage(String.valueOf(livroAPI.language()));

							livroSalvar.setAuthor(author); // ASSOCIA o autor ao livro

							livroRepository.save(livroSalvar); // Salva o LIVRO DEPOIS
							System.out.println("Livro Salvo: " + livroSalvar); // Log para verificar o livro salvo
						}

					} else {
						System.out.println("Nenhum resultado encontrado.");
					}

					break;

				case 2:
					System.out.println("Opção 2 selecionada: Lista de livros registrados\n");
					List<Livro> livros = livroRepository.findAll();
					if (livros.isEmpty()) {
						System.out.println("Nenhum livro registrado.");
					} else {
						livros.forEach(System.out::println); // Imprime a lista de livros
					}
					break;

				case 3:
					System.out.println("Opção 3 selecionada: Lista de autores registrados");
					List<Autor> autores = autorRepository.findAll();
					if (autores.isEmpty()) {
						System.out.println("Nenhum autor registrado.");
					} else {
						for (Autor autor : autores) { // Itera explicitamente para forçar o carregamento
							System.out.println(autor);
						}
					}
					break;

				case 4:
					System.out.println("Opção 4 selecionada: Lista de autores vivos em determinado ano");
					break;

				case 5:
					System.out.println("Opção 5 selecionada: Lista de livros registrados por idioma");
					System.out.print("Digite o idioma: EX. [pt] ou [en]... ");
					String idioma = scanner.nextLine();
					List<Livro> livrosPorIdioma = livroRepository.findByLanguage(idioma);
					if (livrosPorIdioma.isEmpty()){
						System.out.println("Nenhum livro registrado nesse idioma");
					} else {
						livrosPorIdioma.forEach(System.out::println);
					}
					break;

				case 0:
					System.out.println("Saindo...");
					break;

				default:
					System.out.println("Opção inválida. Tente novamente.");
			}
		} while (readOption != 0);
		scanner.close();




	}}

