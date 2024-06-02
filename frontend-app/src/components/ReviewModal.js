import { Modal, ModalContent, ModalHeader, ModalFooter, ModalBody, Button, useDisclosure, Textarea, Input } from "@nextui-org/react";

const ReviewModal = () => {
    const { isOpen, onOpen, onOpenChange } = useDisclosure();

    const handleSubmit = () => {};
    return (
        <>
            <Button onClick={onOpen} className="mb-[2vh] lg:mb-0">Write a Review</Button>
            <Modal
                isOpen={isOpen}
                onOpenChange={onOpenChange}
                placement="top-center"
                className="mt-[25vh] mx-[5vw]"
            >
                <ModalContent>
                    <ModalHeader className="flex flex-col gap-1">Your Review</ModalHeader>
                    <ModalBody>
                        <Input
                        type="number"
                        label="Rating"
                        placeholder="Please rate the space from 1 to 5 stars"
                        variant="bordered"
                        />
                        <Input label="Title "placeholder="Enter the title for review" variant="bordered"/>
                        <Textarea label="Review Content" placeholder="Write your review here..." variant="bordered"/>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="danger" variant="flat" onPress={() => onOpenChange(false)}>
                            Close
                        </Button>
                        <Button color="primary" onPress={handleSubmit}>
                            Submit
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default ReviewModal;