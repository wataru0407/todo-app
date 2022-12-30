type InvalidParam = {
  name: string
  reason: string
};

export type CommonBadRequest = {
  title: string
  detail: string
  invalidParams : InvalidParam[]
}

export type CommonNotFound = {
  title: string
  detail: string
}