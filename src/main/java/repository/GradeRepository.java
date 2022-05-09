package repository;

import domain.Grade;
import domain.Pair;
import validation.Validator;

public class GradeRepository extends AbstractCRUDRepository<Pair<String, String>, Grade> {
    public GradeRepository(Validator<Grade> validator) {
        super(validator);
    }
}
