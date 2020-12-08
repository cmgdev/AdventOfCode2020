package adventOfCode2020.base;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractPuzzle {
	public static final String ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ALPHABET_LOWER = ALPHABET_UPPER.toLowerCase();
	public static final int ASCII_OFFSET = 48;

	private boolean isTest;
	private String answer1;
	private String answer2;
	List<String> input = new ArrayList<>();

	public AbstractPuzzle(boolean isTest) {
		this.isTest = isTest;
		this.input = readFile("#", false);
	}

	public List<String> readFile() {
		return readFile("#", false);
	}

	public List<String> readFile(String comment, boolean includeBlankLines) {
		String fileName = isTest ? "example.txt" : "input.txt";
		String dir = this.getClass().getPackageName().replace(".", "/");
		
		List<String> input = new ArrayList<>();

		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(dir + "/" + fileName);
				BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		) {
			input = buf.lines().filter(i -> !i.startsWith(comment)).collect(Collectors.toList());
			if (!includeBlankLines) input.removeIf(l -> l.isEmpty());
			input = setAnswers(input);
		} catch (Exception e) {
			System.out.println("Oh shit! " + e);
		}
		return input;
	}

	private List<String> setAnswers(List<String> inputs) {
		for (int i = 0; i < inputs.size(); i++) {
			String s = inputs.get(i);
			if (s.startsWith("answer1:")) {
				answer1 = s.split("answer1:")[1].trim();
			} else if (s.startsWith("answer2:")) {
				answer2 = s.split("answer2:")[1].trim();
			}
		}
		return inputs.stream().filter(i -> !i.startsWith("answer")).collect(Collectors.toList());
	}

	public long getAnswer1() {
		return Long.valueOf(answer1);
	}

	public long getAnswer2() {
		return Long.valueOf(answer2);
	}

	public String getAnswer1String() {
		return answer1;
	}

	public String getAnswer2String() {
		return answer2;
	}
	
	public List<String> getInput() {
		return input;
	}
	
	public static <P extends AbstractPuzzle> void solve(P puzzle) {
		System.out.println("Solving 1...");
		puzzle.solve1();
		System.out.println("Solving 2...");
		puzzle.solve2();
	}
	
	public abstract void solve1();
	public abstract void solve2();
	
	
}