package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService taxService;
	
	public ContractService(OnlinePaymentService taxService) {
		this.taxService = taxService;
	}

	public void processContract(Contract contract, int months) {
		double installmentValue = (double)contract.getTotalValue() /months;
		for (int i = 1; i<= months; i++) {
			double initialValue = installmentValue + taxService.interest(installmentValue, i);
			double finalValue = initialValue + taxService.paymentFee(initialValue);
			contract.addInstallment(new Installment(addMonth(contract.getDate(), i), finalValue));
		}
	}

	private Date addMonth(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		return cal.getTime();
	}
}
