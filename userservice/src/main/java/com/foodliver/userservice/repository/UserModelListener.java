package com.foodliver.userservice.repository;

import com.foodliver.userservice.model.User;
import org.springframework.data.mongodb.core.mapping.event.*;
import org.springframework.stereotype.Service;

@Service
public class UserModelListener extends AbstractMongoEventListener<User> {


    private SequenceGeneratorService sequenceGenerator;

    public UserModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        if (event.getSource().getId() == "0") {
            sequenceGenerator.generateSequence(User.SEQUENCE_NAME)
                    .subscribe(id ->
                            event.getSource().setId(Long.toString(id)));
        }
    }

}
