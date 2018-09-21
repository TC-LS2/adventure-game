import React from "react"
import { css } from "emotion"

const buttonClassName = css`
  background: var(--main-color);
  color: var(--background);
  cursor: pointer;

  &:hover {
    background: var(--bold-color);
  }
`

const Button = ({ onClick, children }) => (
  <span onClick={onClick} className={buttonClassName}>
    {children}
  </span>
)

export default Button
