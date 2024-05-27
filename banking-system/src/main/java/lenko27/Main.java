package lenko27;

import lenko27.entities.client.Client;

public class Main {
    public static void main(String[] args) {
        /*Client cliet = new Client("AS","jkj");
        System.out.println(cliet.isSuspicious());
        cliet.setPassportData("sdf");
        System.out.println(cliet.isSuspicious());*/
        //CentralBank cbank = new CentralBank();
        //cbank.createBank(1,2,2,2, 5);
        Client client = Client.builder("John", "Doe")
                .address("123 Street")
                .passportData("ABCD1234")
                //.messages("nk")
                .build();
        //System.out.println(client.getId());

    }
}