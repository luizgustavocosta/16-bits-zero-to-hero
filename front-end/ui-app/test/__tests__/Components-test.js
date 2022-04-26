/**
 * @jest-environment jsdom
 */
import {cleanup, fireEvent, render} from '@testing-library/react';
import LoginComponent from "../../src/component/LoginComponent";
import NewUserComponent from "../../src/component/NewUserComponent";
import Footer from "../../src/Footer";

afterEach(cleanup);

describe('Movie components', () => {
  it('Should render login elements', () => {
    const {getByText} = render(
      <LoginComponent/>,
    );

    expect(getByText('User')).toBeTruthy()
    expect(getByText('Sign in')).toBeTruthy()
    expect(getByText('Create account')).toBeTruthy()
    expect(getByText('Profile')).toBeTruthy()
  }),

    it('Should show the 16 Bits tech', () => {
      const {getByText} = render(
        <Footer/>
      );
      expect(getByText("16 BITS TECH")).toBeTruthy()
    }),

    it('Should have new user components', () => {
      const {getByText, getByTitle, debug} = render(
        <NewUserComponent/>
      );
      expect(getByText("First Name")).toBeTruthy()
      expect(getByText("Confirm")).toBeTruthy()
      expect(getByTitle("Save")).toBeTruthy()
      //debug()
      fireEvent.click(getByTitle("Save"));
      //debug()

    })
})