import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransportCardTest {
    @Test
    void defaultConstructorLeavesFieldsEmpty() {
        TransportCard card = new TransportCard();
        assertNull(card.getOwner());
        assertEquals(0, card.getBalance());
        assertEquals(0, card.getTrips());
        assertEquals(0, card.getSpent());
    }

    @Test
    void constructorRejectsNegativeBalance() {
        TransportCard card = new TransportCard("Ivanov", -50);
        assertEquals("Ivanov", card.getOwner());
        assertEquals(0, card.getBalance());
    }

    @Test
    void topUpIgnoresNonPositiveAmount() {
        TransportCard card = new TransportCard("Ivanov", 300);
        card.topUp(0);
        card.topUp(-100);
        assertEquals(300, card.getBalance());
    }

    @Test
    void topUpAddsPositiveAmount() {
        TransportCard card = new TransportCard("Ivanov", 300);
        card.topUp(200);
        assertEquals(500, card.getBalance());
    }

    @Test
    void isPremiumIsTrueExactlyAt500() {
        assertTrue(new TransportCard("Ivanov", 500).isPremium());
        assertFalse(new TransportCard("Ivanov", 499).isPremium());
    }

    @Test
    void payReducesBalanceAndCountsTrip() {
        TransportCard card = new TransportCard("Ivanov", 500);
        assertTrue(card.pay(35));
        assertEquals(465, card.getBalance());
        assertEquals(1, card.getTrips());
        assertEquals(35, card.getSpent());
    }

    @Test
    void payRejectsAmountGreaterThanBalance() {
        TransportCard card = new TransportCard("Ivanov", 100);
        assertFalse(card.pay(1000));
        assertEquals(100, card.getBalance());
        assertEquals(0, card.getTrips());
        assertEquals(0, card.getSpent());
    }

    @Test
    void averageFareUsesIntegerDivision() {
        TransportCard card = new TransportCard("Ivanov", 500);
        card.pay(35);
        card.pay(35);
        card.pay(40);
        assertEquals(36, card.getAverageFare());
    }

    @Test
    void averageFareIsZeroWithoutTrips() {
        TransportCard card = new TransportCard();
        assertEquals(0, card.getAverageFare());
    }

    @Test
    void copyConstructorMakesIndependentCopy() {
        TransportCard original = new TransportCard("Ivanov", 500);
        original.pay(35);
        TransportCard copy = new TransportCard(original);
        assertEquals(original.getOwner(), copy.getOwner());
        assertEquals(original.getBalance(), copy.getBalance());
        assertEquals(original.getTrips(), copy.getTrips());
        assertEquals(original.getSpent(), copy.getSpent());
        copy.topUp(1000);
        assertEquals(465, original.getBalance());
        assertEquals(1465, copy.getBalance());
    }
}
