package info.moroff.prescriptionmanager.util;

import java.time.LocalDate;
import java.util.Optional;

public class CompareUtils {

	public static int compare(Optional<LocalDate> op1, Optional<LocalDate> op2) {
		if ( op1 == null && op2 == null ) 
			return 0;
		else if ( op1 == null ) 
			return 1;
		else if ( op2 == null )
			return -1;
		else if ( !op1.isPresent() && op2.isPresent() )
			return 1;
		else if ( op1.isPresent() && !op2.isPresent() )
			return -1;
		else			
			return op1.get().compareTo(op2.get());
	}

	public static int compare(LocalDate op1, LocalDate op2) {
		if ( op1 == null && op2 == null ) 
			return 0;
		else if ( op1 == null ) 
			return 1;
		else if ( op2 == null )
			return -1;
		else
			return op1.compareTo(op2);
	}

	public static int compare(Integer op1, Integer op2) {
		if ( op1 == null && op2 == null ) 
			return 0;
		else if ( op1 == null ) 
			return 1;
		else if ( op2 == null )
			return -1;
		else
			return op1.compareTo(op2);
	}

}
