import akka.actor.*;
import scala.concurrent.duration.Duration;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class GoatCommunication {
    public static int i = 0;

    private static void raceGoat(int goatNumber) {
        System.out.println("[Outside loop] Goat " + goatNumber + " - " + i);
        while (i < 100) {
            System.out.println("Goat 3 - " + ++i);
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
            raceGoat(1);
        }

        @Override
        public void onReceive(Object noise) throws Exception {
            Message goat1Noise = (Message) noise;
            System.out.println(goat1Noise.noise);
        }
    }

    public static class Goat2 extends UntypedActor {
        public String goat2Noise = "";

        public Goat2() {
            raceGoat(2);
        }

        @Override
        public void onReceive(Object noise) throws Exception {
            Message goat2Noise = (Message) noise;
            System.out.println(goat2Noise.noise);
        }
    }

    public static class Goat3 extends UntypedActor {
        public String goat3Noise = "";

        public Goat3() {
            raceGoat(3);
        }

        @Override
        public void onReceive(Object noise) throws Exception {
            Message goat3Noise = (Message) noise;
            System.out.println(goat3Noise.noise);

        }
    }


    public static void main(String args[]) {
        final ActorSystem garden = ActorSystem.create("Garden");
        final ActorRef goat1 = garden.actorOf(Props.create(Goat1.class), "Goat1");
        final ActorRef goat2 = garden.actorOf(Props.create(Goat2.class), "Goat2");
        final ActorRef goat3 = garden.actorOf(Props.create(Goat3.class), "Goat3");
        goat1.tell(new Message("1"), ActorRef.noSender());
        goat2.tell(new Message("2"), ActorRef.noSender());
        goat3.tell(new Message("3"), ActorRef.noSender());
        goat3.tell(new Message("4"), ActorRef.noSender());
        garden.shutdown();
    }


}
