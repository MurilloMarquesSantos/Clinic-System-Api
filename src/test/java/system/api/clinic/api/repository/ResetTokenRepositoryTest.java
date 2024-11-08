package system.api.clinic.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import system.api.clinic.api.domain.PasswordResetToken;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static system.api.clinic.api.util.token.ResetTokenCreator.createResetToken;
import static system.api.clinic.api.util.token.ResetTokenCreator.createResetTokenToBeSaved;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ResetTokenRepositoryTest {

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    @Test
    void findByToken_ReturnsOptionalOfPasswordResetToken_WhenSuccessful() {

        PasswordResetToken savedToken = resetTokenRepository.save(createResetTokenToBeSaved());

        Optional<PasswordResetToken> resetToken = resetTokenRepository.findByToken(savedToken.getToken());

        assertThat(resetToken).isPresent().isNotNull().isNotEmpty();

        assertThat(resetToken.get()).isNotNull().isEqualTo(savedToken);
    }

    @Test
    void save_PersistsResetToken_WhenSuccessful() {

        PasswordResetToken savedToken = resetTokenRepository.save(createResetTokenToBeSaved());

        assertThat(savedToken.getToken()).isNotNull().isEqualTo(createResetToken().getToken());

        assertThat(savedToken.getId()).isEqualTo(createResetToken().getId());

        assertThat(savedToken.getExpirationDate()).isNotNull().isEqualTo(createResetToken().getExpirationDate());

    }

}