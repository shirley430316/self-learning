from builtins import range
from builtins import object
import numpy as np

from ..layers import  *
from ..layer_utils import *


class TwoLayerNet(object):
    """
    A two-layer fully-connected neural network with ReLU nonlinearity and
    softmax loss that uses a modular layer design. We assume an input dimension
    of D, a hidden dimension of H, and perform classification over C classes.

    The architecure should be affine - relu - affine - softmax.

    Note that this class does not implement gradient descent; instead, it
    will interact with a separate Solver object that is responsible for running
    optimization.

    The learnable parameters of the model are stored in the dictionary
    self.params that maps parameter names to numpy arrays.
    """

    def __init__(
        self,
        input_dim=3 * 32 * 32,  # dimension of input, i.e. the first layer
        hidden_dim=100,         # dimension of the output of the first layer, also input for second layer
        num_classes=10,
        weight_scale=1e-3,
        reg=0.0,
    ):
        """
        Initialize a new network.

        Inputs:
        - input_dim: An integer giving the size of the input
        - hidden_dim: An integer giving the size of the hidden layer
        - num_classes: An integer giving the number of classes to classify
        - weight_scale: Scalar giving the standard deviation for random
          initialization of the weights.
        - reg: Scalar giving L2 regularization strength.
        """
        self.params = {}
        self.reg = reg

        ############################################################################
        # TODO: Initialize the weights and biases of the two-layer net. Weights    #
        # should be initialized from a Gaussian centered at 0.0 with               #
        # standard deviation equal to weight_scale, and biases should be           #
        # initialized to zero. All weights and biases should be stored in the      #
        # dictionary self.params, with first layer weights                         #
        # and biases using the keys 'W1' and 'b1' and second layer                 #
        # weights and biases using the keys 'W2' and 'b2'.                         #
        ############################################################################
        # *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
        W1 = np.random.normal(0, weight_scale, (input_dim, hidden_dim))
        b1 = np.zeros((hidden_dim))
        W2 = np.random.normal(0, weight_scale, (hidden_dim, num_classes))
        b2 = np.zeros((num_classes))
        self.params["W1"] = W1
        self.params["b1"] = b1
        self.params["W2"] = W2
        self.params["b2"] = b2

        # self.params["input_dim"] = input_dim
        # self.params["hidden_dim"] = hidden_dim
        # self.params["num_classes"] = num_classes
        pass

        # *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
        ############################################################################
        #                             END OF YOUR CODE                             #
        ############################################################################

    def loss(self, X, y=None):
        """
        Compute loss and gradient for a minibatch of data.

        Inputs:
        - X: Array of input data of shape (N, d_1, ..., d_k)
        - y: Array of labels, of shape (N,). y[i] gives the label for X[i].

        Returns:
        If y is None, then run a test-time forward pass of the model and return:
        - scores: Array of shape (N, C) giving classification scores, where
          scores[i, c] is the classification score for X[i] and class c.

        If y is not None, then run a training-time forward and backward pass and
        return a tuple of:
        - loss: Scalar value giving the loss
        - grads: Dictionary with the same keys as self.params, mapping parameter
          names to gradients of the loss with respect to those parameters.
        """
        scores = None
        ############################################################################
        # TODO: Implement the forward pass for the two-layer net, computing the    #
        # class scores for X and storing them in the scores variable.              #
        ############################################################################
        # *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
        scores = np.dot(np.dot(X.reshape(X.shape[0], -1), self.params["W1"]) + self.params["b1"], self.params["W2"]) + self.params["b2"]

        # can use api of affine and relu

        # *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
        ############################################################################
        #                             END OF YOUR CODE                             #
        ############################################################################

        # If y is None then we are in test mode so just return scores
        if y is None:
            return scores

        loss, grads = 0, {}
        ############################################################################
        # TODO: Implement the backward pass for the two-layer net. Store the loss  #
        # in the loss variable and gradients in the grads dictionary. Compute data #
        # loss using softmax, and make sure that grads[k] holds the gradients for  #
        # self.params[k]. Don't forget to add L2 regularization!                   #
        #                                                                          #
        # NOTE: To ensure that your implementation matches ours and you pass the   #
        # automated tests, make sure that your L2 regularization includes a factor #
        # of 0.5 to simplify the expression for the gradient.                      #
        ############################################################################
        # *****START OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
        
        affine1 = affine_forward(X, self.params["W1"], self.params["b1"])
        relu_layer = relu_forward(affine1[0])
        affine2 = affine_forward(relu_layer[0], self.params["W2"], self.params["b2"])
        loss, grad = softmax_loss(affine2[0], y)
        loss += 0.5 * self.reg * (np.sum(self.params["W2"] ** 2) + np.sum(self.params["W1"] ** 2))    ### Pay attention to how regularization term is implemented!!

        L_2_relu, L_2_w2, L_2_b2 = affine_backward(grad, (relu_layer[1], self.params["W2"], self.params["b2"]))  # backward of W2 perceptron
        # L_2_relu += self.reg * np.sum(self.params["W2"])
        L_2_w1 = relu_backward(L_2_relu, affine1[0])
        L_2_x, L_2_w, L_2_b = affine_backward(L_2_w1, (X, self.params["W1"], self.params["b1"]))
        # L_2_w += self.reg * np.sum(self.params["W1"])

        L_2_w += self.reg * self.params["W1"]
        L_2_w2 += self.reg * self.params["W2"]

        grads["W1"] = L_2_w
        grads["b1"] = L_2_b
        grads["W2"] = L_2_w2
        grads["b2"] = L_2_b2
        pass

        # *****END OF YOUR CODE (DO NOT DELETE/MODIFY THIS LINE)*****
        ############################################################################
        #                             END OF YOUR CODE                             #
        ############################################################################

        return loss, grads
