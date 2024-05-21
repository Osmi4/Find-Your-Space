import {MailIcon} from './MailIcon.jsx';
import {LockIcon} from './LockIcon.jsx';
import {Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, useDisclosure, Checkbox, Input, Link} from "@nextui-org/react";


const RegisterModal=()=>{
    const {isOpen, onOpen, onOpenChange} = useDisclosure();

    return (
        <>
        <button onClick={onOpen}>Register</button>
        <Modal 
          isOpen={isOpen} 
          onOpenChange={onOpenChange}
          placement="top-center"
        >
          <ModalContent>
            {(onClose) => (
              <>
                <ModalHeader className="flex flex-col gap-1">Register</ModalHeader>
                <ModalBody>
                <Input
                    autoFocus
                    label="Username"
                    placeholder="Enter your username"
                    variant="bordered"
                  />
                  <Input
                    label="First Name"
                    placeholder="Enter your first name"
                    variant="bordered"
                  />
                  <Input
                    label="Last Name"
                    placeholder="Enter your last name"
                    variant="bordered"
                  />
                  <Input
                    label="Bank Account No."
                    placeholder="Enter your bank account number"
                    variant="bordered"
                  />
                  <Input
                    endContent={
                      <MailIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
                    }
                    label="Email"
                    placeholder="Enter your email"
                    variant="bordered"
                  />
                  <Input
                    endContent={
                      <LockIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
                    }
                    label="Password"
                    placeholder="Enter your password"
                    type="password"
                    variant="bordered"
                  />
                  <Input
                    endContent={
                      <LockIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
                    }
                    label="Confirm Password"
                    placeholder="Enter your password"
                    type="password"
                    variant="bordered"
                  />
                </ModalBody>
                <ModalFooter>
                  <Button color="danger" variant="flat" onPress={onClose}>
                    Close
                  </Button>
                  <Button color="primary" onPress={onClose}>
                    Sign up
                  </Button>
                </ModalFooter>
              </>
            )}
          </ModalContent>
        </Modal>
      </>
    )
}

export default RegisterModal;