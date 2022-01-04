package numeros;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Natural {
	
	private int numero;
	private boolean primo;
	
	public Natural(int numero) {
		// estou com preguiça então pega o módulo mesmo e já tá bão
		if (numero >= 0) this.numero = numero;
		else this.numero = -1*numero;
		
		// verificando se eres primo
		this.primo = ehPrimo(numero);
	}
	
	public static List<Integer> crivoDeErastotenesMat(int numero) {
		List<Integer> listaPrimos = new ArrayList<Integer>();
				
		// começamos criando uma lista com todos os números entre 2 e numero
		for (int i=2; i<=numero; i++) {
			listaPrimos.add(Integer.valueOf(i));
		}
		
		int tam = listaPrimos.size(), i = 0;
		// agora percorro a lista e vou removendo quem tiver divisores além dele próprio 
		while (listaPrimos.get(i) <= Math.sqrt(numero)) {
			Integer p = listaPrimos.get(i); // este elemento É PRIMO (SEMPRE)
			//
			for (int j=i+1; j< tam; j++) {
				// se o elemento na posição i divide o elemento na posição j, então j não é primo e deve ser removido da lista
				if (listaPrimos.get(j) % listaPrimos.get(i) == 0) {
					listaPrimos.remove(j);
					tam--;
				}
			}
			i++;
		}
		return listaPrimos;
	}
	
	public static List<Integer> crivoDeErastotenesMatComp(int numero) {
		List<Integer> listaPrimos = new ArrayList<Integer>();

		// começamos criando um vetor de booleanos que diz quem é primo
		boolean[] ehPrimo = new boolean[numero+1]; 

		// inicialmente todo mundo é suspeito
		for (int i = 2; i <= numero; i++) {
			ehPrimo[i] = true;
		}

		// agora percorro a lista 
		for (int i = 2; i <= Math.sqrt(numero); i++) {
			if (ehPrimo[i]) {
				listaPrimos.add(Integer.valueOf(i));
	
				// e vou desmarcando os múltiplos
				for (int j = 2*i; j <= numero; j+=i) {
					ehPrimo[j]=false;
				}
			}
		}
		return listaPrimos;
	}
	
	private static boolean ehPrimo(int numero) {
		
		if (numero < 0) numero *= -1;
		
		// se for par, não é primo (exceto se for 2)
		if (numero % 2 == 0) {
			if (numero == 2) return true;
			else return false;
		}
		// se até a sua "raiz quadrada" não houver divisores, os números seguintes também não serão divisores
		int parcela = (int)Math.sqrt(numero);
		//
		// testando a divisão por todos os números entre 1 e a raiz quadrada do número
		for (int p=2; p<=parcela; p++) {
			if (numero % p == 0) return false;
		}
		// se não encontrei divisores depois de todo esse rolê, então nosso amigo é primo
		return true;
	}
	
	// Aqui estou simplesmente percorrendo todos os naturais em um dado intervalo e adicionando os
	// primos encontrados em uma lista
	public static List<Integer> listarNumerosPrimos(int primeiro, int ultimo) {
		List<Integer> listaPrimos = new ArrayList<Integer>();
		
		// preguiça de fazer consistência...
		if (primeiro < 0) primeiro *= -1;
		if (ultimo < 0) ultimo *= -1;
		
		// os últimos serão os primeiros!
		if (primeiro > ultimo) {
			int aux = primeiro;
			primeiro = ultimo;
			ultimo = aux;
		}
		
		int anterior = 0;
		for (int i=primeiro;i<=ultimo;i++) {
			if (Natural.ehPrimo(i)) {
				listaPrimos.add(Integer.valueOf(i));				
				//
				// separando em grupos de 10 em 10 para melhorar a visualização
				if (i - (i%10) != anterior - (anterior%10)) {
					System.out.println("");
				}
				//
				System.out.print(String.valueOf(i)+"   ");
				//
				anterior = i;
			}
		}
		System.out.println(" ");
		return listaPrimos;
	}
	
	// percorrendo a lista de primos, imprimindo e colocando uns frufru
	public static void imprimirNumerosPrimos(int primeiro, int ultimo) {
		
		List<Integer> listaPrimos = Natural.listarNumerosPrimos(primeiro, ultimo);
		
		imprimirNumerosPrimos(listaPrimos);
	}
	
	public static void imprimirNumerosPrimos(List<Integer> listaPrimos) {		
		int anterior = 0, tam = 10;
		for (Integer primo : listaPrimos) {
			int i = Integer.valueOf(primo);			
			//
			// estou separando em grupos de 10 em 10 para melhorar a visualização
			if (i - (i%tam) != anterior - (anterior%tam)) {
				System.out.println("");
			}
			System.out.print(Integer.valueOf(primo)+"   ");
			//
			anterior = i;
		}
		System.out.println(" ");
	}
	
	public static List<Integer[]> decompoeFatoresPrimos (int numero) {	
	// obtenho uma lista de todos os primos compreendidos entre 2 e o número que quero decompor
			List<Integer> listaPrimos = Natural.listarNumerosPrimos(2, numero);
			return Natural.decompoeFatoresPrimos(numero, listaPrimos);
	}
	
	public static List<Integer[]> decompoeFatoresPrimos (int numero, List<Integer> listaPrimos) {
		List<Integer[]> decomposicao = new ArrayList<Integer[]>();
		
		// percorro a lista dos primos
		for (Integer primo : listaPrimos) {
			//
			// guardo o quociente do número pelo primo atual
			int quociente = numero / primo;
			//
			// caso este primo seja divisor, guardo ele na primeira posição do vetor e já coloco 1 no expoente
			if (numero % primo == 0) {
				Integer[] fator = new Integer[2];
				fator[0] = primo;
				fator[1] = 1;
				//
				// agora divido o quociente pelo primo para verificar se o quociente também é múltiplo deste primo;
				// se for, o expoente e incrementado
				while (quociente % primo == 0) {
					fator[1]++;
					// obtenho o novo quociente para testar se ele também é divisor deste primo
					quociente = quociente / primo;
				}
				//
				// quanto o resto não for zero, significa que esgotamos todas as possibilidades de dividir este
				// número pelo primo atual, então o expoente na segunda posição do vetor já está correto
				decomposicao.add(fator);
			}
		}
		// ao final, teremos uma lista de vetores tais que a posição 0 é o fator primo e a posição 1 é o seu
		// respectivo expoente 
		return decomposicao;
	}
	
	// frescurinha para trocar true por "sim" e false por "não"
	public static String simOuNao(boolean s) {
		if (s) return "SIM";
		else return "NÃO";
	}
	
	
	public static void main (String Args[]) {
		
		/*
		for (int i=2;i<33554432;i++) {
			System.out.println(i + " é primo? " + Natural.simOuNao(Natural.ehPrimo(i)));
		}
		/**/
		
		//Natural.imprimirNumerosPrimos(2, 25000);
		/**/
		//int inicio = (int)Math.pow(2, 30)-1;
		int inicio = 1;
		int fim = 60;
		//
		System.out.println("Listando números primos entre "+inicio+" e "+fim+": \n");
		//
		// marcando o tempo de execução
		Instant start = Instant.now();
		//System.out.println("ALGORITMO DA EXAUSTÃO");
		List<Integer> listaPrimos = Natural.listarNumerosPrimos(inicio, fim);
		/*
		for (Integer p : listaPrimos) System.out.println(String.valueOf(p)+"    ");
		System.out.println("\n");
		/**/
		System.out.println("\nALGORITMO DA EXAUSTÃO");
		System.out.println("Quantidade de primos entre 2 e "+fim+" = "+String.valueOf(listaPrimos.size()));
		//
		/*
		for (int numo=inicio;numo<=fim;numo++) {
			int c=0;
			System.out.print(numo + " = ");
			List<Integer[]> fatPrim = Natural.decompoeFatoresPrimos(numo,listaPrimos);
			for (Integer[] primo : fatPrim) {
				if (c++ != 0) System.out.print(" x "); 
				System.out.print(primo[0] + "^" + primo[1]);
			}
			if (fatPrim.size() == 1 && fatPrim.get(0)[1] == 1) System.out.print(" (PRIMO) ");
			System.out.println("\n");
		}/**/
		/**/
		// marcando o tempo de execução
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		//
		System.out.print("Tempo de execução: ");
		if (timeElapsed.toDays() > 0) System.out.println(timeElapsed.toDays()+" dias.");
		else if (timeElapsed.toHours() > 0) System.out.println(timeElapsed.toHours()+" horas.");
		else if (timeElapsed.toMinutes() > 0) System.out.println(timeElapsed.toMinutes()+" minutos.");
		else if (timeElapsed.toSeconds() > 0) System.out.println(timeElapsed.toSeconds()+" segundos.");
		else if (timeElapsed.toMillis() > 0) System.out.println(timeElapsed.toMillis()+" milissegundos.");
		else if (timeElapsed.toNanos() > 0) System.out.println(timeElapsed.toNanos()+" nanossegundos.");
		/**/
		System.out.println("\n");
		// marcando o tempo de execução
		start = Instant.now();
		System.out.println("ALGORITMO DE ERASTÓTENES SIMILAR AO MATEMATICO");
		//
		List<Integer> listaPrimosE = Natural.crivoDeErastotenesMat(fim);
		//Natural.imprimirNumerosPrimos(listaPrimosE);
		System.out.println("Quantidade de primos entre 2 e "+fim+" = "+String.valueOf(listaPrimosE.size()));
		//
		// marcando o tempo de execução
		end = Instant.now();
		timeElapsed = Duration.between(start, end);
		//
		System.out.print("Tempo de execução: ");
		if (timeElapsed.toDays() > 0) System.out.println(timeElapsed.toDays()+" dias.");
		else if (timeElapsed.toHours() > 0) System.out.println(timeElapsed.toHours()+" horas.");
		else if (timeElapsed.toMinutes() > 0) System.out.println(timeElapsed.toMinutes()+" minutos.");
		else if (timeElapsed.toSeconds() > 0) System.out.println(timeElapsed.toSeconds()+" segundos.");
		else if (timeElapsed.toMillis() > 0) System.out.println(timeElapsed.toMillis()+" milissegundos.");
		else if (timeElapsed.toNanos() > 0) System.out.println(timeElapsed.toNanos()+" nanossegundos.");
		/**/
		System.out.println("\n");
		// marcando o tempo de execução
		start = Instant.now();
		System.out.println("ALGORITMO DE ERASTÓTENES COMPUTACIONALMENTE EFICIENTE");
		//
		List<Integer> listaPrimosB = Natural.crivoDeErastotenesMat(fim);
		//Natural.imprimirNumerosPrimos(listaPrimosB);
		System.out.println("Quantidade de primos entre 2 e "+fim+" = "+String.valueOf(listaPrimosB.size()));
		//
		// marcando o tempo de execução
		end = Instant.now();
		timeElapsed = Duration.between(start, end);
		//
		System.out.print("Tempo de execução: ");
		if (timeElapsed.toDays() > 0) System.out.println(timeElapsed.toDays()+" dias.");
		else if (timeElapsed.toHours() > 0) System.out.println(timeElapsed.toHours()+" horas.");
		else if (timeElapsed.toMinutes() > 0) System.out.println(timeElapsed.toMinutes()+" minutos.");
		else if (timeElapsed.toSeconds() > 0) System.out.println(timeElapsed.toSeconds()+" segundos.");
		else if (timeElapsed.toMillis() > 0) System.out.println(timeElapsed.toMillis()+" milissegundos.");
		else if (timeElapsed.toNanos() > 0) System.out.println(timeElapsed.toNanos()+" nanossegundos.");
		/**/
	}

}
