import os.path
import pickle
import sys
from typing import Union
# import time

import torch
from transformers import AutoTokenizer, T5ForConditionalGeneration, T5TokenizerFast


class CommentGeneratorLoaded:
    """
    Class that generates comments to given codelines. 
    """

    def __init__(self, path: str="script\\models\\smfile.pth"):
        self.MODEL_NAME = "t5-base"
        self.model = self.get_saved_model(path)

        self.code_tokenizer, self.nl_tokenizer = self.tokenizers()


    def define_model(self) -> T5ForConditionalGeneration:
        """
        Creates T5 model.

        :return: Returns T5 model. 
        """
        pass
        model = T5ForConditionalGeneration.from_pretrained(self.MODEL_NAME)

        return model


    def get_saved_model(self, path: str) -> T5ForConditionalGeneration:
        """
        Loads saved model by a given path.

        :param path: Path to saved .pth model.

        :return: Returns loaded model.
        """
        model = torch.load(path, map_location=torch.device('cpu'))
        model.eval()

        return model


    def tokenizers(self) -> Union[T5TokenizerFast, T5TokenizerFast]:
        """
        Initializes tokenizers for the model.

        :return: Returns initialized tokenizers.
        """
        tokenizer_code = AutoTokenizer.from_pretrained(self.MODEL_NAME, model_max_length=512)
        tokenizer_nl = AutoTokenizer.from_pretrained(self.MODEL_NAME, model_max_length=512)

        return tokenizer_code, tokenizer_nl


    def generate(self, text: str) -> str:
        """
        Generates comment using pretrained saved model by the given codelines.

        :param text: Codelines to generate a comment. 

        :return: Returns generated string comment.
        """
        input_ids = self.code_tokenizer(text, return_tensors="pt").input_ids
        outputs = self.model.generate(input_ids, max_length=50)

        return "\n//".join(self.nl_tokenizer.decode(outputs[0], skip_special_tokens=True).split("//"))[1:]
    # model.cuda()  


def preload(path: str="script\\models\\pkfile.pkl") -> CommentGeneratorLoaded:
    """
    Method create and serializes CommentGeneratorLoaded instance if it called first time.
    Next times the field is deserialized and returned.

    :param path: path to save serialized model.

    :return: CommentGeneratorLoaded instance.
    """
    if not os.path.isfile(path):
        scommenter = CommentGeneratorLoaded()
        with open(path, "wb") as f:
            pickle.dump((scommenter), f)
        return scommenter
    
    else:
        with open(path, "rb") as f:
            commenter = pickle.load(f)
            return commenter
    


if __name__ == "__main__":
    code_line = sys.argv[1]
    # start = time.time()
    commenter = preload()

    print(commenter.generate(code_line))
    # end = time.time() print(end - start)