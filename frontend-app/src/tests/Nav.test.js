import { render, fireEvent, within } from '@testing-library/react';
import Nav from '../Nav.js'; 
import { MemoryRouter, Route, Routes  } from 'react-router-dom';
import '@testing-library/jest-dom';

import { cleanup } from '@testing-library/react';

afterEach(() => {
  cleanup();
});

test('handleChange updates searchInput', () => {
  const { getByPlaceholderText } = render(
    <MemoryRouter>
      <Nav />
    </MemoryRouter>
  );
  const input = getByPlaceholderText('Search');

  fireEvent.change(input, { target: { value: 'test' } });

  expect(input.value).toBe('test');
});

test('useEffect updates filteredSpaces based on searchInput', () => {
    const { getByPlaceholderText, getByText } = render(
      <MemoryRouter>
        <Nav />
      </MemoryRouter>
    );
  
    const input = getByPlaceholderText('Search');
    fireEvent.change(input, { target: { value: 'W' } });
  
    expect(getByText('Wroclaw Main Square-Cube')).toBeInTheDocument();
  });

  const Space = ({ id }) => <div>Space {id}</div>;

test('search results table displays based on searchInput', () => {
  const { getByPlaceholderText, queryByTestId, getByText } = render(
    <MemoryRouter initialEntries={['/']}>
      <Routes>
        <Route path="/" element={<Nav />} />
        <Route path="/space/:id" element={<Space id={2} />} />
      </Routes>
    </MemoryRouter>
  );

  const input = getByPlaceholderText('Search');
  fireEvent.change(input, { target: { value: 'W' } });

  const searchResults = queryByTestId('search-results');
  expect(searchResults).toBeInTheDocument();

  const searchScope = within(searchResults);
  const tableText = searchScope.getByText('Wroclaw Main Square-Cube');

  fireEvent.click(tableText);

  const spaceText = getByText('Space 2');
  expect(spaceText).toBeInTheDocument(); // Confirm that we navigated to the correct component

  fireEvent.change(input, { target: { value: '' } });
  expect(queryByTestId('search-results')).toBeNull();
});

test('searching for one city can display multiple results', () => {
  const { getByPlaceholderText, getByText } = render(
    <MemoryRouter>
      <Nav />
    </MemoryRouter>
  );

  const input = getByPlaceholderText('Search');
  fireEvent.change(input, { target: { value: 'Warsaw' } });

  expect(getByText('Warsaw-Marszalkowska')).toBeInTheDocument();
  expect(getByText('Warsaw-Wola')).toBeInTheDocument();
});