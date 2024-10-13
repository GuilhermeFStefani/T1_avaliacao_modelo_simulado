package sma.interfaces;

import java.util.Collection;

public record IQueue(String id,
        Integer servers,
        Integer capacity,
        IInterval exit,
        IInterval entry,
        Float first_entry,
        Collection<INetwork> networks) {
}
