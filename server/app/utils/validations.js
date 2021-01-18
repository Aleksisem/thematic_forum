import crypto from 'crypto'

const genSalt = (length) => {
  return crypto.randomBytes(Math.ceil(length/2))
          .toString('hex')
          .slice(0, length)
}

const hashPassword = (password, salt) => {
  return password = crypto.createHmac('sha256', salt)
                    .update(password)
                    .digest('hex')
}

const isEmpty = (text) => {
  if (text === undefined || text === '') {
    return true
  }
  if (text.replace(/\s/g, '').length) {
    return false
  }
  return true
}

const empty = (text) => {
  if (text === undefined || text === '') {
    return true
  }
}

const comparePassword = (a, b) => {
  return a === b
}

export {
  genSalt,
  hashPassword,
  isEmpty,
  empty,
  comparePassword
}