import akka.actor.*;
import scala.concurrent.duration.Duration;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class GoatCommunication {
    public static int i = 0;

    private static void printSenderAndMessage(Message noise, String senderName, String receiverName) {
        Message goatNoise = (Message) noise;
        System.out.println(senderName + " gave " + receiverName + " this message '" + goatNoise.noise + "'");
    }

    private static void raceGoat(int goatNumber) {
        System.out.println("[Outside loop] Goat " + goatNumber + " - " + i);
        while (i < 100) {
            System.out.println("Goat " + goatNumber + " - " + ++i);
        }
    }

    public static class Message implements Serializable {
        public String noise;

        public Message(String noise) {
            this.noise = noise;
        }
    }

    public static class Goat1 extends UntypedActor {
        public String goat1Noise = "";

        public Goat1() {
            raceGoat(1); //Race check for same resources
        }

        @Override
        public void onReceive(Object noise) throws Exception {
            printSenderAndMessage((Message) noise, sender().path().name(), self().path().name());
        }
    }

    public static class Goat2 extends UntypedActor {
        public String goat2Noise = "";

        public Goat2() {
            raceGoat(2); //Race check for same resources
        }

        @Override
        public void onReceive(Object noise) throws Exception {
            printSenderAndMessage((Message) noise, sender().path().name(), self().path().name());
        }
    }

    public static class Goat3 extends UntypedActor {
        public String goat3Noise = "";

        public Goat3() {
            raceGoat(3); //Race check for same resources
        }

        @Override
        public void onReceive(Object noise) throws Exception {
            printSenderAndMessage((Message) noise, sender().path().name(), self().path().name());

        }
    }


    public static void main(String args[]) {
        final ActorSystem garden = ActorSystem.create("Garden");
        final ActorRef goat1 = garden.actorOf(Props.create(Goat1.class), "Goat1");
        final ActorRef goat2 = garden.actorOf(Props.create(Goat2.class), "Goat2");
        final ActorRef goat3 = garden.actorOf(Props.create(Goat3.class), "Goat3");
        goat1.tell(new Message("Hi Goat 1"), goat2);
        goat2.tell(new Message("How are you Goat 2"), goat2);
        goat3.tell(new Message("Hey Even Goat 3 is here"), goat1);
        goat3.tell(new Message("Hi Goat 3"), goat2);
        garden.shutdown();
    }


}
