import { useState } from "react";
import { Modal, ModalContent, ModalHeader, ModalFooter, ModalBody, Button, useDisclosure, Textarea, Input } from "@nextui-org/react";
import axios from "axios";
import { useParams } from "react-router-dom";

const ReviewModal = ({reviews}) => {
    const { isOpen, onOpen, onOpenChange } = useDisclosure();
    const { id } = useParams();
    const [rating, setRating] = useState("");
    const [title, setTitle] = useState("");
    const [comment, setComment] = useState("");

    const handleSubmit = async () => {
        const token = localStorage.getItem('authToken');
        if (token) {
            try {
                const response = await axios.post("http://localhost:8080/api/rating/", {
                    score: rating,
                    comment: `${title}: ${comment}`,
                    spaceId: id
                }, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                reviews(prevReviews => [...prevReviews, response.data])
                if (response.status === 200) {
                    // Handle successful submission
                    onOpenChange(false); // Close the modal
                }
            } catch (error) {
                console.error("Error submitting review:", error);
            }
        }
    };

    return (
        <>
            <Button onClick={onOpen} className="mb-[2vh] lg:mb-0 bg-black font-semibold" color="primary">Write a Review</Button>
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
                            value={rating}
                            onChange={(e) => setRating(e.target.value)}
                        />
                        <Input
                            label="Title"
                            placeholder="Enter the title for review"
                            variant="bordered"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                        />
                        <Textarea
                            label="Review Content"
                            placeholder="Write your review here..."
                            variant="bordered"
                            value={comment}
                            onChange={(e) => setComment(e.target.value)}
                        />
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
