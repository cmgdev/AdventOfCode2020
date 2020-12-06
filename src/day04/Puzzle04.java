package day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import base.AbstractPuzzle;

public class Puzzle04 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;
	public static final int DAY = 4;

	public Puzzle04() {
		super(IS_TEST, DAY);
	}

	@Override
	public void solve1() {
		List<String> rawPassportData = readFile("//", true);
		
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
		System.out.println(validCount == getAnswer1());
	}

	@Override
	public void solve2() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		AbstractPuzzle.solve(new Puzzle04());

	}

}
