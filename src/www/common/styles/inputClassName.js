import { css } from "emotion"

const inputClassName = css`
  border: solid 2px var(--bold-color);
  color: var(--bold-color);
  background: var(--dark-color);
  width: 100%;

  &:focus {
    outline: none;
    border-color: var(--bolder-color);
  }
`

export default inputClassName
