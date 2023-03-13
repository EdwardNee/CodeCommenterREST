import sys

def test(code_line: str):
    return "That is test file: " + code_line


if __name__ == "__main__":
    code_line = sys.argv[1]
    print(test(code_line))
