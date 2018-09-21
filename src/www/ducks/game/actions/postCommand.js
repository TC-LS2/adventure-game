export const POST_COMMAND = "POST_COMMAND"
export const postCommand = (username, command, args) => ({
  type: POST_COMMAND,
  username,
  command,
  arguments: args,
})
