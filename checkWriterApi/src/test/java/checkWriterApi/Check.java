package checkWriterApi;

import java.time.LocalDate;
import java.util.Date;

public class Check {

	public Check(String bankAccountId, String payeeId, String categoryId, Double amount) {
		
		this.bankAccountId = bankAccountId;
		this.payeeId = payeeId;
		this.categoryId = categoryId;
		this.amount = amount;
	}

	private String bankAccountId;
	private String payeeId;
	private String categoryId;
	private Double amount;
	private String issueDate;
	private String checkId;
	
	private String memo;

	public String getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getDate() {
		return issueDate;
	}

	public void setDate(String date) {
		this.issueDate = date;
	}
	
	public String toString() {
		String line = "checkID" + bankAccountId+ "bankAccountId: " + bankAccountId + ", payeeId: " + payeeId + ", categoryId: " + categoryId + ", amount: " + amount + "\n";
		return line;
	}

	
}
