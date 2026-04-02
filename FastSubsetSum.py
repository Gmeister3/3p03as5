import sys
import os


def fast_subset_sum(X, T):
    """
    FastSubsetSum algorithm from Erickson's Algorithms (2019).
    Uses a set-based dynamic programming approach.
    Returns True if there is a subset of X with sum exactly T.
    """
    S = {0}
    for x in X:
        S = S | {s + x for s in S}
        S = {s for s in S if s <= T}
    return T in S


def main():
    if len(sys.argv) < 2:
        print("Usage: python FastSubsetSum.py <input_file>")
        sys.exit(1)

    input_file = sys.argv[1]

    try:
        with open(input_file, 'r') as f:
            lines = f.read().split()
    except OSError as e:
        print(f"Error reading input file: {e}")
        sys.exit(1)

    try:
        n = int(lines[0])
        if len(lines) < n + 2:
            raise ValueError(f"Expected {n + 2} tokens, got {len(lines)}")
        X = [int(lines[i + 1]) for i in range(n)]
        T = int(lines[n + 1])
    except (ValueError, IndexError) as e:
        print(f"Error parsing input: {e}")
        sys.exit(1)

    result = fast_subset_sum(X, T)

    os.makedirs('outputs', exist_ok=True)

    input_filename = os.path.basename(input_file)
    output_filename = os.path.join('outputs', 'output ' + input_filename)

    with open(output_filename, 'w') as f:
        f.write('TRUE\n' if result else 'FALSE\n')

    print('TRUE' if result else 'FALSE')


if __name__ == '__main__':
    main()
