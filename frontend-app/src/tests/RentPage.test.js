import { render, fireEvent, within } from '@testing-library/react';
import RentPage from "../pages/RentPage"
import '@testing-library/jest-dom';
import { MemoryRouter } from 'react-router-dom';

test('Page rendered correctly', () => {
 
    const { getByPlaceholderText } = render(
        <MemoryRouter>
          <RentPage />
        </MemoryRouter>
      );
    const firstName = getByPlaceholderText('Enter your first name');
    expect(firstName).toBeInTheDocument();

    const lastName = getByPlaceholderText('Enter your last name');
    expect(lastName).toBeInTheDocument();

    const address = getByPlaceholderText("Enter your address");
    expect(address).toBeInTheDocument();
  });