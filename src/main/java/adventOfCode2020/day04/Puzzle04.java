package adventOfCode2020.day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle04 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;

	public Puzzle04(boolean isTest) {
		super(isTest);
	}

	@Override
	public Object solve1() {
		List<String> rawPassportData = readFile("//", true);
		
		List<String> passports = convertToPassports(rawPassportData);
		/*
		    byr (Birth Year)
		    iyr (Issue Year)
		    eyr (Expiration Year)
		    hgt (Height)
		    hcl (Hair Color)
		    ecl (Eye Color)
		    pid (Passport ID)
		    cid (Country ID)
		 */
		List<String> requiredFields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
		int validCount = 0;
		for( String passport : passports ) {
			boolean allFieldsPresent = requiredFields.stream().allMatch( f -> passport.contains(f + ":"));
			validCount += allFieldsPresent ? 1 : 0;
		}
		
		System.out.println("Valid passports: " + validCount);
		System.out.println(validCount == getExpectedAnswer1());
		return validCount;
	}
	
	private String getFieldValue( String[] passportFields, String field) {
		for (String passportField : passportFields) {
			if (passportField.startsWith(field)) {
				return passportField.replaceAll(field, "");
			}
		}
		return null;
	}

	private List<String> convertToPassports(List<String> rawPassportData) {
		List<String> passports = new ArrayList<>();
		String currentPassport = "";
		for( String rawPassport : rawPassportData ) {
			if( !rawPassport.isBlank() ) {
				currentPassport = currentPassport.concat(rawPassport + " ");
			}
			else {
				passports.add(currentPassport);
				currentPassport = "";
			}
		}
		return passports;
	}

	@Override
	public Object solve2() {
		List<String> rawPassportData = readFile("//", true);
		List<String> passports = convertToPassports(rawPassportData);

		int validPassports = 0;
		for (String passport : passports) {
			System.out.println("Checking passport: " + passport);
			String[] passportFields = passport.split(" ");
			if (validBirthYear(passportFields) &&
					validIssueYear(passportFields) &&
					validExpirationYear(passportFields) &&
					validHeight(passportFields) &&
					validHairColor(passportFields) &&
					validEyeColor(passportFields) &&
					validPassportId(passportFields)) {
				validPassports++;
			}
			System.out.println("---");
		}

		System.out.println("Valid passports: " + validPassports);
		System.out.println(validPassports == getExpectedAnswer2());
		return validPassports;
	}
	
	private boolean validBirthYear(String[] passportFields) {
		String field = "byr:";
		String fieldValue = getFieldValue(passportFields, field);
		if (fieldValue != null) {
			try {
				int birthYear = Integer.parseInt(fieldValue);
				if (birthYear >= 1920 && birthYear <= 2002) {
					System.out.println("Valid birthYear: " + birthYear);
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	private boolean validIssueYear(String[] passportFields) {
		String field = "iyr:";
		String fieldValue = getFieldValue(passportFields, field);
		if (fieldValue != null) {
			try {
				int issueYear = Integer.parseInt(fieldValue);
				if (issueYear >= 2010 && issueYear <= 2020) {
					System.out.println("Valid issueYear: " + issueYear);
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	private boolean validExpirationYear(String[] passportFields) {
		String field = "eyr:";
		String fieldValue = getFieldValue(passportFields, field);
		if (fieldValue != null) {
			try {
				int expirationYear = Integer.parseInt(fieldValue);
				if (expirationYear >= 2020 && expirationYear <= 2030) {
					System.out.println("Valid expirationYear: " + expirationYear);
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	private boolean validHeight(String[] passportFields) {
		String field = "hgt:";
		String fieldValue = getFieldValue(passportFields, field);
		if (fieldValue != null) {
			if (fieldValue.endsWith("cm")) {
				try {
					int val = Integer.parseInt(fieldValue.replace("cm", "").trim());
					if (val >= 150 && val <= 193) {
						System.out.println("Valid height cm: " + val);
						return true;
					}
				} catch (Exception e) {
					return false;
				}
			}
			if (fieldValue.endsWith("in")) {
				try {
					int val = Integer.parseInt(fieldValue.replace("in", "").trim());
					if (val >= 59 && val <= 76) {
						System.out.println("Valid height in: " + val);
						return true;
					}
				} catch (Exception e) {
					return false;
				}
			}
		}
		return false;
	}
	
	private boolean validHairColor(String[] passportFields) {
		String field = "hcl:";
		String fieldValue = getFieldValue(passportFields, field);
		if (fieldValue != null) {
			if (fieldValue.matches("^#[a-f0-9]{6}$")) {
				System.out.println("Valid hair color: " + fieldValue);
				return true;
			}
		}
		return false;
	}
	
	private boolean validEyeColor(String[] passportFields) {
		String field = "ecl:";
		List<String> validEyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
		String fieldValue = getFieldValue(passportFields, field);
		if (fieldValue != null) {
			if (validEyeColors.contains(fieldValue)) {
				System.out.println("Valid eye color: " + fieldValue);
				return true;
			}
		}
		return false;
	}
	
	private boolean validPassportId(String[] passportFields) {
		String field = "pid:";
		String fieldValue = getFieldValue(passportFields, field);
		if (fieldValue != null) {
			if (fieldValue.matches("^[0-9]{9}$")) {
				System.out.println("Valid passport id: " + fieldValue);
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		AbstractPuzzle.solve(new Puzzle04(IS_TEST));

	}

}
