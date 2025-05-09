from builtins import range
import numpy as np
from random import shuffle
from past.builtins import xrange


def softmax_loss_naive(W, X, y, reg):
    """
    Softmax loss function, naive implementation (with loops)

    Inputs have dimension D, there are C classes, and we operate on minibatches
    of N examples.

    Inputs:
    - W: A numpy array of shape (D, C) containing weights.
    - X: A numpy array of shape (N, D) containing a minibatch of data.
    - y: A numpy array of shape (N,) containing training labels; y[i] = c means
      that X[i] has label c, where 0 <= c < C.
    - reg: (float) regularization strength

    Returns a tuple of:
    - loss as single float
    - gradient with respect to weights W; an array of same shape as W
    """
    # Initialize the loss and gradient to zero.
    loss = 0.0
    dW = np.zeros_like(W)

    #############################################################################
    # TODO: Compute the softmax loss and its gradient using explicit loops.     #
    # Store the loss in loss and the gradient in dW. If you are not careful     #
    # here, it is easy to run into numeric instability. Don't forget the        #
    # regularization!                                                           #
    #############################################################################
    # *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
    for i in range(X.shape[0]):
      score = X[i].dot(W)
      normalized_score = np.exp(score[y[i]]) / np.sum(np.exp(score))
      loss -= np.log(normalized_score)

      p = np.exp(score) / np.sum(np.exp(score))
      dW += np.dot(X[i].reshape(-1,1), p.reshape(1,-1))
      dW[:, y[i]] -= X[i]

    pass
    loss /= X.shape[0]
    loss += 0.5 * reg * np.sum(W**2)

    dW /= X.shape[0]
    dW += reg * W
    # *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

    return loss, dW


def softmax_loss_vectorized(W, X, y, reg):
    """
    Softmax loss function, vectorized version.

    Inputs and outputs are the same as softmax_loss_naive.
    """
    # Initialize the loss and gradient to zero.
    loss = 0.0
    dW = np.zeros_like(W)

    #############################################################################
    # TODO: Compute the softmax loss and its gradient using no explicit loops.  #
    # Store the loss in loss and the gradient in dW. If you are not careful     #
    # here, it is easy to run into numeric instability. Don't forget the        #
    # regularization!                                                           #
    #############################################################################
    # *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
    score = X.dot(W)  # (N, C) -- sample_num, class_num
    normalized_score = np.exp(score) / np.sum(np.exp(score), axis=1).reshape(X.shape[0], -1)
    loss = np.sum(-np.log(normalized_score[np.arange(X.shape[0]), y]))

    loss /= X.shape[0]
    loss += 0.5 * np.sum(W**2)


    # 做好预处理，和SVM hinge function一起整理
    # 原错误做法
    # dW += np.dot(X.T, normalized_score)
    # dW[:, y] -= X[y]
    # why this is wrong?

    p = np.copy(normalized_score)
    p[np.arange(X.shape[0]), y] -=1
    dW += np.dot(X.T, p)

    dW /= X.shape[0]
    dW += reg * W
    pass

    # *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****

    return loss, dW
