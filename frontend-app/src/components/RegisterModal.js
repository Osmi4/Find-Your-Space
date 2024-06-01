import { Modal, ModalContent, ModalHeader, ModalFooter, Button, useDisclosure } from "@nextui-org/react";
import { useAuth0 } from '@auth0/auth0-react';

const RegisterModal = () => {
    const { isOpen, onOpen, onOpenChange } = useDisclosure();
    const { loginWithRedirect } = useAuth0();

    const handleRegister = () => {
        loginWithRedirect({
            screen_hint: 'signup',
        });
    };

    return (
        <>
            <button onClick={onOpen}>Register</button>
            <Modal
                isOpen={isOpen}
                onOpenChange={onOpenChange}
                placement="top-center"
                className="mt-[25vh] mx-[5vw]"
            >
                <ModalContent>
                    <ModalHeader className="flex flex-col gap-1">Register</ModalHeader>
                    <ModalFooter>
                        <Button color="danger" variant="flat" onPress={() => onOpenChange(false)}>
                            Close
                        </Button>
                        <Button color="primary" onPress={handleRegister}>
                            Sign up with Auth0
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default RegisterModal;
