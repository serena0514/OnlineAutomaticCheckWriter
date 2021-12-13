package checkWriterApi;

import java.util.List;

public class Checks {
	List<Check> checks;

	public Checks(List<Check> checks) {
		this.checks = checks;
	}
	
	public String toString() {
		String result ="";
		for (int i = 0; i< checks.size(); i++) {
			Check c = checks.get(i);
			 String bankAccountId = c.getBankAccountId();
			 String payeeId = c.getPayeeId();
			 String categoryId = c.getCategoryId();
			 Double amount = c.getAmount();
			 String line = "bankAccountId: " + bankAccountId + ", payeeId: " + payeeId + ", categoryId: " + categoryId + ", amount: " + amount + "\n";
			 result = result + line;
		}
		return result;
	}
}
